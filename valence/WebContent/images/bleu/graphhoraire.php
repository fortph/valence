<?php
header("content-type: image/png");
$jja = $_GET["jja"];
$imagea = imagecreate($jja * 10, 10);
if($jja > 35){
	imagecolorallocate($imagea,255,0,0);
	}
	else
	{
	imagecolorallocate($imagea,80,140,160);
	}
imagepng ($imagea)

/*header("content-type: image/png");
$jjb = $_GET["jjb"];
$image = imagecreate($jjb * 10, 10);
if($jjb > 35){
	imagecolorallocate($image,255,0,0);
	}
	else
	{
	imagecolorallocate($image,80,140,160);
	}
imagepng ($image)*/
?>