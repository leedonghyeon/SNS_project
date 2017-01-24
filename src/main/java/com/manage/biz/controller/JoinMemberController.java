package com.manage.biz.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.manage.biz.service.JoinMemberService;
import com.manage.biz.vo.JoinMember;
import javax.servlet.http.HttpServletRequest;

/**
 * Handles requests for the application home page.
 */
@Controller
public class JoinMemberController {
	
	@Autowired
	JoinMemberService joinmemberService;

	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws Exception 
	 */

	@RequestMapping("/application")
	public String IntroPage(Locale locale, Model model) throws Exception {
		return "sns/JoinMembership"; //views->sns->JoinMembership.jsp
	}

	@RequestMapping("/pass")
	public String IntroPage1(Locale locale, Model model) throws Exception {

		return "sns/FindPassword"; //views->sns->Findpassword.jsp
	}
	
	@RequestMapping("/findpassword")//비밀번호 찾기
	public String FindPassword(JoinMember joinmember, Model model) throws Exception {

		int rtn=joinmemberService.findPassword(joinmember);
		if(rtn==1)
		{
			model.addAttribute("joinmember", joinmember);
			model.addAttribute("msg", "change your password.");
			return "sns/UpdatePassword";
		}
		else
		{	
			model.addAttribute("msg1", "틀렸습니다.");
			return "sns/FindPassword";	
		}
	}
	//
	@RequestMapping("/updatepassword") //비밀번호 수정
	public String UpdatePassword(JoinMember joinmember, Model model) throws Exception {
		System.out.println("service start");
		joinmemberService.updatePassword(joinmember);
		System.out.println("service end");
		return "sns/loginpage";
	}

	@RequestMapping("/insert") 
	public String JoinMemberList(JoinMember joinmember, Model model) throws Exception {
		joinmemberService.insertJoinMember(joinmember);
		return "sns/loginpage"; //views->sns->loginpage.jsp
	}

	@RequestMapping("/loginForm")
    public String loginForm(){
        return "sns/loginpage";
    }

    @RequestMapping("/loginProcess")
	public ModelAndView loginProcess(JoinMember user, HttpSession session, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:loginForm");
		
		JoinMember loginUser = joinmemberService.findByUserIdAndPassword(user.getMember_id(), user.getMember_password());
		
		if (loginUser != null) { //session check
			session.setAttribute("userLoginInfo", loginUser);
		}
		return mav;
	}

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.setAttribute("userLoginInfo", null);
        return "redirect:loginForm";
    }
}

