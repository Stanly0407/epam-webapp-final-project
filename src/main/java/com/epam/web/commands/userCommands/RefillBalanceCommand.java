package com.epam.web.commands.userCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RefillBalanceCommand implements Command {
    private static final String USER_ACCOUNT_PAGE_COMMAND = "/controller?command=userAccount";
    private static final String REFILL_BALANCE_PAGE = "/WEB-INF/view/userPages/refillBalancePage.jsp";
    private static final String SUCCESSFUL_VALIDATION = "successful";
    private static final String PARAMETER_USER_ID = "userId";

    private final UserService userService;

    public RefillBalanceCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String paymentAmount = request.getParameter("paymentAmount");
        String cardNumberPartOne = request.getParameter("cardNumberPartOne");
        String cardNumberPartTwo = request.getParameter("cardNumberPartTwo");
        String cardNumberPartThree = request.getParameter("cardNumberPartThree");
        String cardNumberPartFour = request.getParameter("cardNumberPartFour");
        String cardNumber = cardNumberPartOne + cardNumberPartTwo + cardNumberPartThree + cardNumberPartFour;
        String nameOnCard = request.getParameter("nameOnCard");
        String lastnameOnCard = request.getParameter("lastnameOnCard");
        String cvv = request.getParameter("cvv");
        String validatePaymentDetailsMessage = userService.validatePaymentDetails(paymentAmount, cardNumber, nameOnCard, lastnameOnCard, cvv);
        if (SUCCESSFUL_VALIDATION.equals(validatePaymentDetailsMessage)) {
            HttpSession session = request.getSession();
            Long userId = (Long) session.getAttribute(PARAMETER_USER_ID);
            userService.refillUserBalance(paymentAmount, userId);
            return CommandResult.redirect(USER_ACCOUNT_PAGE_COMMAND);
        } else {
            request.setAttribute(validatePaymentDetailsMessage, true);
            return CommandResult.forward(REFILL_BALANCE_PAGE);
        }
    }

}
