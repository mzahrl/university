%
% Copyright 2017 Vienna University of Technology.
% Institute of Computer Graphics and Algorithms.
%

function [result, asShotNeutral, fnc_read_file_info, fnc_tranform_colors] = evc_black_level(input, filename)
% This function is our main function. It executes all functions, that
% were implemented by you, in the correct order.
% ATTENTION: You are not allowed to change this function!

	fnc_read_file_info  = @(filename) 			evc_read_file_info (filename);
	fnc_tranform_colors = @(input, blackLevel) 	evc_transform_colors(input, blackLevel);
	
	[blackLevel, asShotNeutral] = evc_read_file_info(filename);	
	result 						= evc_transform_colors(input, blackLevel);
end

function [blackLevel, asShotNeutral] = evc_read_file_info(filename)
%evc_read_file_info extracts the black level (blackLevel) and the neutral
% white value (asShotNeutral) from the image file specified by filename.
%
%   INPUT
%   filename... 		filename of the image 
%
%   OUTPUT
%   blackLevel... 		black level, which is stored in the image infos 
%                       (see imfinfo)
%   asShotNeutral... 	neutral white value, which is stored in the image 
%                       infos (see imfinfo)

	%HINT: 	The function 'imfinfo' might be useful.
	%NOTE:  The following two lines can be removed. They prevent the 
    %       framework from crashing.
    
    %TODO:  Implement this function.
	x = imfinfo(filename);
	blackLevel = x.BlackLevel;
    asShotNeutral = x.AsShotNeutral;
end

function [result] = evc_transform_colors(input, blackLevel)    
%evc_transform_colors adjusts the contrast such that black (blackLevel and 
% values below) becomes 0 and white becomes 1.
% The white value of the input image is 65535.
%
%   INPUT
%   input...            input image
%   blackLevel... 		black level of the input image
%
%   OUTPUT
%   result... 			image in double format where all values are 
%                       transformed from the interval [blackLevel, 65535] 
%                       to [0, 1]. All values below the black level have to
%                       be 0.
	
	%NOTE:  The following line can be removed. It prevents the framework
    %       from crashing.
    
    %TODO:  Implement this function.
    matrix = input;
    result = double(matrix - blackLevel) / (65535 - blackLevel);
end