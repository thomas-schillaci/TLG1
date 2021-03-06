#version 120

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;
uniform vec4 u_color;

varying vec4 v_color;

void main() {
    gl_Position = projection * view * model * gl_Vertex;
    v_color = u_color;
}