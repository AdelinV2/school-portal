    $(function () {
        var start = moment().add(1, 'day').endOf('day');
        var end = moment().add(1, 'day').endOf('day');
        function cb(start, end) {
            $('#reportrange span').html(start.format('MMM D') + ' - ' + end.format('MMM D'));
        }
        $('#reportrange').daterangepicker({
            startDate: start,
            endDate: end
        }, cb);
        cb(start, end);
    });