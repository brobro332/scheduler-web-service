<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../layout/user/header.jsp"%>

<img src="/image/signin-spap.png" style="display: block; position: relative; width: 200px; left: 42%">

<br/>

<div class="container" style="display: inline-block; position: relative; left: 35%; width: 70%;">
    <form>
        <div class="form-group">
            <label>이메일</label>
            <input type="email" class="form-control" placeholder="이메일" id="email" value="${principal.user.email}" style="width:402px;" readOnly>
            <p id="valid_email"></p>
        </div>


        <div class="form-group">
            <label>비밀번호</label>
            <input type="password" class="form-control" placeholder="비밀번호" id="password" style="width:402px;">
            <p id="valid_password"></p>
            <input type="password" class="form-control" placeholder="확인용 비밀번호" id="checkedPassword" style="width:402px;">
            <p id="valid_checkedPassword"></p>
        </div>

        <div class="form-group">
        <label>이름</label>
            <input type="name" class="form-control" placeholder="이름" id="name" style="width:402px;">
            <p id="valid_name"></p>
        </div>

        <div class="form-group">
        <label>휴대전화번호</label>
            <input type="phone" class="form-control" placeholder="휴대전화번호" id="phone" style="width:402px;">
            <p id="valid_phone"></p>
        </div>
    </form>

    <br/>

    <button class="btn btn" id="btn-update" style="background-color: #956be8; color: white; width: 402px;">등록</button>
    <br/><br/><br/>
</div>

<script src="/js/user.js"></script>
<%@ include file="../layout/user/footer.jsp"%>