<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="composePopup" class="compose-popup">
    <form action="compose" method="post">
        <button class="close-popup" type="button" onclick="document.getElementById('composePopup').classList.remove('show')">&times;</button>
        <h2>New Email</h2>
        <label for="composeTo">To:</label>
        <input type="email" id="composeTo" name="to" required>

        <label for="composeSubject">Subject:</label>
        <input type="text" id="composeSubject" name="subject" required>

        <label for="composeBody">Body:</label>
        <textarea id="composeBody" name="body" rows="6" required></textarea>
		
		<input type="hidden" id="status" name="status" value="sent">
        <button type="submit" onclick="document.getElementById('status').value='sent'">Send</button>
        <button type="submit" onclick="document.getElementById('status').value='draft'">Draft</button>
    </form>
</div>
