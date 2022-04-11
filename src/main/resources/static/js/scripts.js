$(document).ready(function () {

    $('.dynamic-update-requirements').on('click', 'button[data-dynamic-update-requirements-url]', function () {
        let url = $(this).data('dynamic-update-requirements-url');

        let formData = $('form').serializeArray();
        let param = {};
        param["name"] = $(this).attr('name');
        param["value"] = $(this).val();
        formData.push(param);

        // updating the dynamic section
        $('#dynamicRequirements').load(url, formData);
    });

    $('.dynamic-update-technologies').on('click', 'button[data-dynamic-update-technologies-url]', function () {
            let url = $(this).data('dynamic-update-technologies-url');

            let formData = $('form').serializeArray();
            let param = {};
            param["name"] = $(this).attr('name');
            param["value"] = $(this).val();
            formData.push(param);

            $('#dynamicTechnologies').load(url, formData);
        });
});