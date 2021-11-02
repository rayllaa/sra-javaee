 //<![CDATA[
    var grid = $('.p-grid'),
        cols = grid.children('div'),
        iconInfo = $('.iconInfo'),
        copiedText = $('.copied-text'),
        closeIcon = $('.closeInfo-close'),
        colsClicked = false;
 
    $(function() {                
        cols.off('click').on('click', function(e) {
            showIconInfo(e);
            colsClicked = true;
        });
         
        $(document.body).off('click').on('click', function(e) {
            if(!colsClicked && iconInfo.is(':visible') && !iconInfo.has($(e.target)).length) {
                iconInfo.hide();
            }
            colsClicked = false;
        });
    });
 
    function onFilter(e) {
        var value = $(e.target).val();
 
        if (value == "") {
            cols.show();
        } 
        else {
            cols.hide();
            cols.filter('[data-name*="' + value.toLowerCase() + '"]').show();
        }
    }
     
    function showIconInfo(e) {
        var container = $(e.target),
        iconName = "";
         
        if(!container.hasClass('icon-container')) {
            container = container.parent();
        }
 
        var iconName = container.data('name'),
        iconKey = parseInt(container.data('key')).toString(16),
        iconSampleInput = iconInfo.children('.icon-sample-input'),
        iconSampleText = '<i class="pi pi-' + iconName + '"></i>';
         
        iconInfo.children('.icon-text').text('pi-' + iconName + " (code: \"\\" + iconKey + "\")");
        iconSampleInput.val(iconSampleText);
        iconSampleInput.next().remove().end().after(iconSampleText);
         
        if(iconInfo.is(':hidden')) {
            iconInfo.show();
        }
    }
     
    function copyText(e) {
        $(e.target).select();
        document.execCommand('copy');
        copiedText.fadeIn('slow');
        copiedText.fadeOut(2000);
    }
     
    function closeInfo(e) {
        iconInfo.hide();
        colsClicked = false;
        e.preventDefault();
    }