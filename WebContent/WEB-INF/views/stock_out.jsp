<%@include file="../includes/header.jsp"%>
<%@include file="../includes/nav-header.jsp"%>

<div class="form-container">
	<div class="form-inner">


		<h5 class="card-header info-color white-text text-center py-4">
			<strong>Update Stock Out Details</strong>
		</h5>

		<form class="form-cnt">
			<div class="form-row">
				<div class="form-group col-md-6">
					<label for="stock-out-date">Stock out Date</label> <input
						type="date" class="form-control" id="stock-out-date">
				</div>
				<div class="form-group col-md-6">
					<label for="school">School</label> <select id="school"
						class="form-control">
						<option selected>Choose...</option>
						<option>...</option>
					</select>
				</div>
			</div>
			<div class="form-row">
				<div class="form-group col-md-6">
					<label for="carton_no">Carton No </label> <input type="text"
						class="form-control" id="carton_no">
				</div>
				<div class="form-group col-md-6">
					<label for="box_no">Box No</label> <input type="text"
						class="form-control" id="box_no">
				</div>
			</div>
			<div class="form-group">
				<label for="box-group">Box Group</label> <input type="text"
					class="form-control" id="box-group">
			</div>
			<div class="form-row">
				<div class="form-group col-md-6">
					<label for="box_sn">Box Serial Number</label> <input type="text"
						class="form-control" id="box_sn">
				</div>
				<div class="form-group col-md-6">
					<label for="card_sn">Card Serial Number</label> <input type="text"
						class="form-control" id="card_sn">
				</div>
			</div>
			<div class="form-row">
				<div class="form-group col-md-6">
					<label for="printed-date">Printed Date</label> <input type="date"
						class="form-control" id="printed-date">
				</div>
				<div class="form-group col-md-6">
					<label for="school">Status</label> <input type="text"
						class="form-control" id="status" value="Active">
				</div>
			</div>
			<button type="submit" class="btn submit-btn">Update</button>
		</form>

	</div>
</div>

<%@include file="../includes/footer.jsp"%>