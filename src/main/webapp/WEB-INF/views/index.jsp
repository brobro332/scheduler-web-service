<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="layout/user/header.jsp"%>

<div class="container-fluid" style="height: 300px;">
<div>
    <div style="display: inline-block; position: absolute; left: 20%; top: 13%;">
        <div style="width:400px; height:300px;">
        <a style="color: gray;"><small>Welcome to SAPP<small></a>
        <h1>당신의 일주일을<br/>
        <kbd id="kbd">가치있게,</kbd><br/>
        저희가 도와드립니다!</h1>
        <br/>
        <button id="loginForm" class="btn btn-lg" style="background-color: #c1aef5; color: white;"><b>회원가입</b></button>
        </div>
    </div>
</div>

<div style="display: inline-block; position: absolute; left: 60%; top: 10%;">
<img src="images/banner-right-sapp.png" style="width:300px;">
</div>
</div>

<div class="container-fluid" style="display: inline-block; position: relative; left: 20%;">
    <b>이 영역에 내용 추가하기<br/>
    어떤 서비스를 제공하는지<br/>
    기능명, 간단한 설명, 클릭하면 해당 메뉴로 들어가게끔(로그인한 사람에 한해서)</b>
</div>


<%@ include file="layout/user/footer.jsp"%>