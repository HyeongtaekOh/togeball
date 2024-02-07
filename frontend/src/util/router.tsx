import Login from "src/pages/login";
import Home from "src/pages/home";
import Dev from "src/pages/dev";
import RecruitPost from "src/pages/recruit/post";
import Recruit from "src/pages/recruit";
import Signup from "src/pages/signup";
import Profile from "src/pages/profile";
import Boards from "src/pages/boards";
import Calendar from "src/pages/calendar";
import MyPage from "src/pages/mypage";
import Matching from "src/pages/matching";
import Chat from "src/pages/chat";
import Todaygames from "src/pages/todaygames";
import { OAuthRedirect } from "src/pages/login/components";

export const RouterInfo = [
  {
    children: [
      { path: "/", element: <Home /> },
      { path: "/login", element: <Login /> },
      { path: "/dev", element: <Dev /> },
      { path: "/home", element: <Home /> },
      { path: "/recruit/post", element: <RecruitPost /> },
      { path: "/recruit", element: <Recruit /> },
      { path: "/signup", element: <Signup /> },
      { path: "/profile", element: <Profile /> },
      { path: "/todaygames", element: <Todaygames /> },
      { path: "/boards", element: <Boards /> },
      { path: "/calendar", element: <Calendar /> },
      { path: "/mypage", element: <MyPage /> },
      { path: "/matching", element: <Matching /> },
      { path: "/chat", element: <Chat /> },
      {
        path: "/login/kakao",
        element: <OAuthRedirect />, // 또는 <KakaoRedirectComponent /> 등으로 변경
      },
      
    ]
  }
]