// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

jQuery(document).ready(function(){

const distanceToNextImage = -72;
let currentImageNumber = 0;

$(".lightbox").hide();
$("#greeting-container").hide();

$(".item").click(function() {
  currentImageNumber = parseInt($(this).attr("id"), 10);
  $("#carousel-strip").css("left", currentImageNumber * distanceToNextImage + "vw");
  $(".lightbox").show();
});

//Left and right
$("#right").click(function() {
  currentImageNumber = (currentImageNumber + 1) % 12;
  $("#carousel-strip").css("left", currentImageNumber * distanceToNextImage + "vw");
});

$("#left").click(function() {
  currentImageNumber = currentImageNumber == 0 ? 11 : (currentImageNumber - 1);
  $("#carousel-strip").css("left", currentImageNumber * distanceToNextImage + "vw");
});

$("#overlay").click(function() {
  $(".lightbox").hide();
});
});

function getComments() {
    $("#greeting-container").show();
  fetch('/data').then(response => response.json()).then((strArr) => {
    document.getElementById('comments').innerText = strArr;
  });
}

function showComments() {
    $("#greeting-container").show();
}

function hideComments() {
    $("#greeting-container").hide();
}

function getTranslation() {
    fetch('/data/translation').then(response => response.json()).then((strArr) => {
    document.getElementById('comments').innerText = strArr;
  });
}


