(function() {
	var customDateDDMMMYYYYToOrd = function(date) {
		var dateParts = date.split("-");
		return (dateParts[2] * 10000)
				+ ($.inArray(dateParts[1].toUpperCase(), [ "01", "02", "03",
						"04", "05", "06", "07", "08", "09", "10", "11", "12" ]) * 100)
				+ (dateParts[0] * 1);
	};
	jQuery.fn.dataTableExt.aTypes.unshift(function(sData) {
		if (/^([0-2]?\d|3[0-1])-(01|02|03|04|05|06|07|08|09|10|11|12)-\d{4}/i
				.test(sData)) {
			return "date-dd-mmm-yyyy";
		}
		return null;
	});
	jQuery.fn.dataTableExt.oSort["date-dd-mmm-yyyy-asc"] = function(a, b) {
		var ordA = customDateDDMMMYYYYToOrd(a), ordB = customDateDDMMMYYYYToOrd(b);
		return (ordA < ordB) ? -1 : ((ordA > ordB) ? 1 : 0);
	};
	jQuery.fn.dataTableExt.oSort["date-dd-mmm-yyyy-desc"] = function(a, b) {
		var ordA = customDateDDMMMYYYYToOrd(a), ordB = customDateDDMMMYYYYToOrd(b);
		return (ordA < ordB) ? 1 : ((ordA > ordB) ? -1 : 0);
	};
})();