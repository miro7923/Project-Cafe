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
			'len': 70
		},
		success: function(data) {
			if (data != null)
			{
				for (var i = 0; i < data.length; i++)
				{
					var titleId = '#mainTitle';
					titleId += (i + 1);
					$(titleId).html(data[i].title);

					var contentId = '#mainContent';
					contentId += (i + 1);
					$(contentId).html(data[i].content);

					var hrefId = '#mainHref';
					hrefId += (i + 1);
					$(hrefId).attr('href', './BoardContent.bo?num='+data[i].num+'&pageNum=1');
				}
			}
		}
	});
}