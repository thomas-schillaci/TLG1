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
varying float v_random;

void main() {
    float r = 1.0 - pow((abs(2.0 * gl_Vertex.x / 640 - 1.0) - 1.0), 1.0) * pow((abs(2.0 * gl_Vertex.y / 360 - 1.0) - 1.0), 1.0);
    mat4 distortion;
    r = 1.0;
    distortion[0] = vec4(r, 0.0, 0.0, 0.0);
    distortion[1] = vec4(0.0, r, 0.0, 0.0);
    distortion[2] = vec4(0.0, 0.0, 1.0, 0.0);
    distortion[3] = vec4(0.0, 0.0, 0.0, 1.0);

    gl_Position = distortion * projection * view * model * gl_Vertex;

    v_intensity = min(1.0, a_intensity + mod(gl_Position.x*128, 2)/20);
    v_bright = u_bright;
    v_dark = u_dark;
}