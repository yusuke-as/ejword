package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.WordDAO;
import model.Word;

@WebServlet("/main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int LIMIT=20;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String searchWord=(String)request.getParameter("searchWord");
		if(searchWord!=null) {
			String mode=(String)request.getParameter("mode");
			if(mode==null) {
				mode="startsWith";
			}
			String page=(String)request.getParameter("page");
			int pageNo=page==null ? 1:Integer.parseInt(page);
			WordDAO dao=new WordDAO();
			int total=dao.getCount(searchWord,mode);
			List<Word> list=dao.getListBySearchWord(searchWord, mode,LIMIT,(pageNo-1)*LIMIT);
			request.setAttribute("total", total);
			request.setAttribute("limit", LIMIT);
			request.setAttribute("searchWord",searchWord);
			request.setAttribute("mode",mode);
			request.setAttribute("list", list);
			request.setAttribute("pageNo", pageNo);
			/*
			if(total>LIMIT) {
				int pageCount=total%LIMIT ==0 ? total/LIMIT:total/LIMIT+1;
				String link="";
				StringBuilder sb=new StringBuilder();
				sb.append("<div class='paginationBox'>\n");
				sb.append("<ul class='pagination'>\n");
				if(pageCount<20){
					for(int i=1;i<=pageCount;i++){
						link="/ejword/main?searchWord="+searchWord+"&mode="+mode+"&page="+i;
						sb.append("<li class='"+(pageNo==i? "active":"") +"'><a href='"+link+"'>"+i+"</a></li>\n");
					}
				}else{
					//大量にページがある場合先頭へのリンクを追加する
					link="/ejword/main?searchWord="+searchWord+"&mode="+mode+"&page="+1;
					sb.append("<li class='"+(pageNo==1? "disabled":"") +"'><a href='"+link+"' aria-label='Start'><span aria-hidden='true'>&laquo;</span></a></li>\n");
					//現在ページから前後５件を表示
					for(int i=pageNo-5;i<=pageNo+5;i++){
						if(i<1 || i>pageCount){continue;}
						link="/ejword/main?searchWord="+searchWord+"&mode="+mode+"&page="+i;
						sb.append("<li class='"+(pageNo==i? "active":"") +"'><a href='"+link+"'>"+i+"</a></li>\n");
					}
					//最後へのリンクを追加する
					link="/ejword/main?searchWord="+searchWord+"&mode="+mode+"&page="+pageCount;
					sb.append("<li class='"+(pageNo==total/LIMIT+1? "disabled":"") +"'><a href='"+link+"' aria-label='End'><span aria-hidden='true'>&raquo;</span></a></li>\n");
				}
				sb.append("</ul>\n");
				sb.append("</div>\n");
				request.setAttribute("pagination", sb.toString());
			}
			*/
		}
	RequestDispatcher rd=request.getRequestDispatcher("/WEB-INF/view/main2.jsp");
		rd.forward(request, response);
	}
}

