/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {
    
    // On click of a Superhero "Delete" link.
    $(document).on('click', '.deleteSuperhuman', onClickDeleteSuperhuman);
    $(document).on('click', '.deleteSuperpower', onClickDeleteSuperpower);
    $(document).on('click', '.deleteLocation', onClickDeleteLocation);
    $(document).on('click', '.deleteOrganization', onClickDeleteOrganization);
    $(document).on('click', '.deleteSighting', onClickDeleteSighting);
});

function onClickDeleteSuperhuman(event) {
    var superhumanId = $(this).data('id');
    $('#confirmSuperhumanDelete').modal('show');
    
    $(document).on('click', '#deleteSuperhumanSubmit', function(event) {
        $.ajax({
            method: 'DELETE',
            url: 'superhuman/' + superhumanId,
            success: function() {
                alert('Superhuman successfully deleted.');
               $('#confirmSuperhumanDelete').modal('hide');
               location.reload();
           },
            error: function() {
                $('#confirmSuperhumanDelete').modal('hide');
                alert('Unable to delete superhuman. Superhuman is referenced by existing Sightings, which must be updated first before the superhuman can be properly deleted.');
                location.reload();
           }
        });
    });
}

function onClickDeleteSuperpower(event) {
    var superpowerId = $(this).data('id');
    $('#confirmSuperpowerDelete').modal('show');
    
    $(document).on('click', '#deleteSuperpowerSubmit', function(event) {
        $.ajax({
           url: 'superpower/' + superpowerId,
           method: 'DELETE',
           success: function() {
               alert('Superpower successfully deleted.');
                $('#confirmSuperpowerDelete').modal('hide');
                location.reload();
           }, 
           error: function() {
               alert('Unable to delete superpower. Superpower is referenced by existing Superheroes, who must be updated first before the superpower can be properly deleted.');
                location.reload();
           }
        });
    });
}

function onClickDeleteLocation(event) {
    var locationId = $(this).data('id');
    $('#confirmLocationDelete').modal('show');
    
    $(document).on('click', '#deleteLocationSubmit', function(event) {
        $.ajax({
            url: 'location/' + locationId,
           method: 'DELETE',
           success: function() {
               alert('Location successfully deleted.');
                $('#confirmLocationDelete').modal('hide');
                location.reload();
           }, 
           error: function() {
               alert('Unable to delete location. Location is referenced by existing Sightings and/or Organizations, which must be updated first before the location can be properly deleted.');
               location.reload();
           }
        });
    });
}

function onClickDeleteOrganization(event) {
    var organizationId = $(this).data('id');
    $('#confirmOrganizationDelete').modal('show');
    
    $(document).on('click', '#deleteOrganizationSubmit', function (event) {
        $.ajax({
          url: 'organization/' + organizationId,
          method: 'DELETE',
          success: function() {
              alert('Organization successfully deleted.');
                $('#confirmOrganizationDelete').modal('hide');
                location.reload();
          }, 
          error: function() {
              alert('Unable to delete organization. Organization is referenced by existing Superheroes, who must be updated first before the organization can be properly deleted.');
              location.reload();
          }
       });
    });
}

function onClickDeleteSighting(event) {
    var organizationId = $(this).data('id');
    $('#confirmSightingDelete').modal('show');
    
    $(document).on('click', '#deleteSightingSubmit', function (event) {
        $.ajax({
          url: 'sighting/' + organizationId,
          method: 'DELETE',
          success: function() {
              alert('Sighting successfully deleted.');
              $('#confirmSightingDelete').modal('hide');
                location.reload()
          }, 
          error: function() {
              alert('Unable to delete Sighting.');
              location.reload();
          }
       });
    });
}