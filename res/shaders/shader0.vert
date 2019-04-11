#version 120

varying vec4 color;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main() {
    gl_Position = projection * view * model * gl_Vertex;
    color = gl_Color;
}