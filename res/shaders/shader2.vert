#version 120

attribute float a_intensity;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

uniform vec3 u_bright;
uniform vec3 u_dark;

varying float v_intensity;
varying vec3 v_bright;
varying vec3 v_dark;

void main() {
    gl_Position = projection * view * model * gl_Vertex;
    v_intensity = a_intensity;
    v_bright = u_bright;
    v_dark = u_dark;
}