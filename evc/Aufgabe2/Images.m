%
% Copyright 2017 Vienna University of Technology.
% Institute of Computer Graphics and Algorithms.
%

function[image_swapped, image_mark_green, image_masked, image_reshaped, image_convoluted, image_edge] = Images()

%% Initialization. Do not change anything here
input_path = 'lena_color.jpg';
output_path = 'lena_output.png';

image_swapped = [];
image_mark_green = [];
image_masked = [];
image_reshaped = [];
image_edge = [];


%% I. Images basics
% 1) Load image from 'input_path'
%TODO: Add your code here
bild = imread(input_path);
% 2) Convert the image from 1) to double format with range [0, 1]. DO NOT USE LOOPS.
%TODO: Add your code here
bild = im2double(bild);
% 3) Use the image from 2) to create an image where the red and the green channel
% are swapped. The result should be stored in image_swapped. DO NOT USE LOOPS.
%TODO: Add your code here
r=(bild(:,:,1));
g=(bild(:,:,2));
b=(bild(:,:,3));
image_swapped = cat(3, g, r, b);
% 4) Display the swapped image
%TODO: Add your code here
imshow(image_swapped);
% 5) Write the swapped image to the path specified in output_path. The
% image should be in png format.
%TODO: Add your code here
imwrite(image_swapped,output_path);
% 6) Create logical image where every pixel is marked that has a green channel
% which is greater or equal 0.7. The result should be stored in image_mark_green. 
% Use the image from step 2 for this step.
% DO NOT USE LOOPS.
% HINT:
% see http://de.mathworks.com/help/matlab/matlab_prog/find-array-elements-that-meet-a-condition.html).
g=(bild(:,:,2));
image_mark_green = g >= 0.7;
%TODO: Add your code here

% 7) Set all pixels in the original image (the double image from step 2) to black where image_mark_green is
% true (where green >= 0.7). Store the result in image_masked. 
% You can use 'repmat' to complete this task. DO NOT USE LOOPS. 
r(image_mark_green)=0;
g(image_mark_green)=0;
b(image_mark_green)=0;
nbild=cat(3, r, g, b);
imshow(nbild);
%TODO: Add your code here

% 8) Convert the original image (the double image from step 2) to a grayscale image and reshape it from
% 512x512 to 1024x256. Cut off the right half of the image and attach it to the bottom of the left half.
% The result should be stored in 'image_reshaped' DO NOT USE LOOPS.
% (Hint: Matlab adresses matrices with "height x width")
%TODO: Add your code here
bild = rgb2gray(bild);
image_reshaped = [bild(:, 1:256, :); bild(:, 257:512, :)];
%% II. Filters and convolutions

% 1) Use fspecial to create a 5x5 gaussian filter with sigma=2.0
%TODO: Delete the next line and add your code here
gfilter = fspecial('gaussian',[5 5],2)

% 2) Implement the evc_filter function. You are allowed to use loops for
% this task. You can assume that the kernel is always of size 5x5.
% For pixels outside the image use 0. 
% Do not use the conv or the imfilter or similar functions here. The result should be
% stored in image_convoluted
% The output image should have the same size as the input image.
%image_convoluted = evc_filter(image_swapped, gauss_kernel);

% 3) Create a image showing the horizontal edges in image_reshaped using the sobel filter.
% For this task you can use imfilter/conv.
% Attention: Do not use evc_filter for this task!
% The result should be stored in image_edge. DO NOT USE LOOPS.
% The output image should have the same size as the input image.
% For this task it is your choice how you handle pixels outside the
% image, but you should use a typical method to do this.
%TODO: Add your code here
x = fspecial('sobel');
image_edge = imfilter(image_reshaped,x,0);
imshow(image_edge)
end

% Returns the input image filtered with the kernel
% input: An rgb-image
% kernel: The filter kernel
function [result] = evc_filter(input, kernel)

    %TODO: Add your code here
    result = input;

end
