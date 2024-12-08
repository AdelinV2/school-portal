$(function(){
    
    let datePicker = document.getElementById('datePicker');
    let picker = new Lightpick({
        field: datePicker,
        onSelect: function(date){
            datePicker.value = date.format('Do MMMM YYYY');
        }
    });
    
    let dateRangePicker = document.getElementById('dateRangePicker');
    let pickerRange = new Lightpick({
        field: dateRangePicker,
        singleDate: false,
        onSelect: function(start, end){
            let str = '';
            str += start ? start.format('Do MMMM YYYY') + ' to ' : '';
            str += end ? end.format('Do MMMM YYYY') : '...';
            dateRangePicker.value = str;
        }
    });
});
