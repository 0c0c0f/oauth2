package org.springframework.security.oauth.examples.sparklr.mvc;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.security.oauth2.provider.approval.Approval.ApprovalStatus;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.endpoint.WhitelabelApprovalEndpoint;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller for retrieving the model for and displaying the confirmation page for access to a protected resource.
 *
 * @author Ryan Heaton
 */
@Controller
@SessionAttributes("authorizationRequest")
public class AccessConfirmationController {

    private ClientDetailsService clientDetailsService;

    private ApprovalStore approvalStore;
    private WhitelabelApprovalEndpoint endpoint = new WhitelabelApprovalEndpoint();
    private Map<String, String> parameters = new HashMap<String, String>();

    private AuthorizationRequest createFromParameters(Map<String, String> authorizationParameters) {
        AuthorizationRequest request = new AuthorizationRequest(authorizationParameters, Collections.<String, String>emptyMap(),
                authorizationParameters.get(OAuth2Utils.CLIENT_ID),
                OAuth2Utils.parseParameterList(authorizationParameters.get(OAuth2Utils.SCOPE)), null,
                null, false, authorizationParameters.get(OAuth2Utils.STATE),
                authorizationParameters.get(OAuth2Utils.REDIRECT_URI),
                OAuth2Utils.parseParameterList(authorizationParameters.get(OAuth2Utils.RESPONSE_TYPE)));
        return request;
    }

    @RequestMapping("/oauth/confirm_access")
    public ModelAndView getAccessConfirmation(Map<String, Object> model, Principal principal, HttpServletRequest request) throws Exception {
        WhitelabelApprovalEndpoint whitelabelApprovalEndpoint = new WhitelabelApprovalEndpoint();
        String expectedContent = "<html><body><h1>OAuth Approval</h1><p>Do you authorize \"client\" to access your protected resources?</p>" +
                "<form id=\"confirmationForm\" name=\"confirmationForm\" action=\"/foo/oauth/authorize\" method=\"post\">" +
                "<input name=\"user_oauth_approval\" value=\"true\" type=\"hidden\"/><input type=\"hidden\" name=\"_csrf\" value=\"FOO\" /><ul>" +
                "<li><div class=\"form-group\">scope.read: <input type=\"radio\" name=\"scope.read\" value=\"true\" checked>Approve</input> " +
                "<input type=\"radio\" name=\"scope.read\" value=\"false\">Deny</input></div></li></ul><label>" +
                "<input name=\"authorize\" value=\"Authorize\" type=\"submit\"/></label></form></body></html>";
        //request.setContextPath("/foo");
        String scope = request.getParameter("scope");
        request.setAttribute("_csrf", new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", "FOO"));
        parameters.put("client_id", "client");
        model.put("authorizationRequest", createFromParameters(parameters));
        model.put("scopes", Collections.singletonMap(scope, "true"));
        ModelAndView result = whitelabelApprovalEndpoint.getAccessConfirmation(model, request);
        return result;
    }

    @RequestMapping("/oauth/error")
    public String handleError(Map<String, Object> model) throws Exception {
        // We can add more stuff to the model here for JSP rendering. If the client was a machine then
        // the JSON will already have been rendered.
        model.put("message", "There was a problem with the OAuth2 protocol");
        return "oauth_error";
    }

    public void setClientDetailsService(ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }

    public void setApprovalStore(ApprovalStore approvalStore) {
        this.approvalStore = approvalStore;
    }

}