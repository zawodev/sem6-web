jQuery(document).ready(function($) {
    $('#reload-ad').on('click', function() {
        $.ajax({
            url: randomAdsAjax.ajax_url,
            type: 'POST',
            data: {
                action: 'get_random_ad'
            },
            success: function(response) {
                $('#ad-container').html(response);
            }
        });
    });
});
