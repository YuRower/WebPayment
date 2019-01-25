<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="New card" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<table id="main-container">

		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr>

			<td class="content center">
				<h3>
					<fmt:message key="newcard.header" />
				</h3>
				<button class="collapsible" style="background-color: gold">Get
					info about Golden Card</button>
				<div class="content_text">
					<p>The gold card adds to the account 2 percent of the total
						amount of the deposit.</p>
				</div>
				<form action="controller" method="POST">
					<input type="hidden" name="command" value="createNewCard" /> <input
						type="hidden" name="card_type" value="gold" /> <input
						type="submit" value="<fmt:message key="newcard.submit"/>">
				</form>
			</td>
		</tr>

		<tr>
			<td class="content center">
				<button class="collapsible" style="background-color: silver">Get
					info about Silver Card</button>
				<div class="content_text">
					<p>The silver card adds to the account 1 percent of the total
						amount of the deposit.</p>
				</div>
				<form action="controller" method="POST">
					<input type="hidden" name="command" value="createNewCard" /> <input
						type="hidden" name="card_type" value="silver" /> <input
						type="submit" value="<fmt:message key="newcard.submit"/>">
				</form>
			</td>
		</tr>

		<tr>
			<td class="content center">
				<button class="collapsible">Get info about Default Card</button>
				<div class="content_text">
					<p>The default card adds to the account -1 percent of the total
						amount of the deposit.</p>
				</div>
				<form action="controller" method="POST">
					<input type="hidden" name="command" value="createNewCard" /> <input
						type="hidden" name="card_type" value="default" /> <input
						type="submit" value="<fmt:message key="newcard.submit"/>">
				</form>
			</td>
		</tr>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</table>
	<script>
		var coll = document.getElementsByClassName("collapsible");
		var i;
		for (i = 0; i < coll.length; i++) {
			coll[i].addEventListener("click", function() {
				this.classList.toggle("active");
				var content = this.nextElementSibling;
				if (content.style.display === "block") {
					content.style.display = "none";
				} else {
					content.style.display = "block";
				}
			});
		}
	</script>
</body>
</html>