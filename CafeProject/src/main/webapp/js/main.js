$(document).ready(function()
{
	getFeeds();
});

function getFeeds()
{
	$.ajax({
		type: 'POST',
		async: false,
		url: './GetFeed.bo',
		dataType: 'json',
		data: {
			'cnt': 3,
			'len': 71
		},
		success: function(data) {
			if (data != null)
			{
				for (var i = 0; i < data.length; i++)
				{
					var titleId = '#mainTitle';
					var contentId = '#mainContent';
					titleId += (i + 1);
					contentId += (i + 1);
					$(titleId).html(data[i].title);
					$(contentId).html(data[i].content);
				}
			}
		}
	});
}