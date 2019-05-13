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

float cosh(float x) {
    return (exp(x)+exp(-x))/2;
}

void main() {
    float a =  0.0020577467;
    float b = 0.0036582163;
    float r = 1.0 - (2.0 - cosh(a*(gl_Vertex.x-640))) * (2.0 - cosh(a*(gl_Vertex.y-360)));
    r/=20;
    r+=0.95;
    mat4 distortion;
    distortion[0] = vec4(r, 0.0, 0.0, 0.0);
    distortion[1] = vec4(0.0, r, 0.0, 0.0);
    distortion[2] = vec4(0.0, 0.0, 1.0, 0.0);
    distortion[3] = vec4(0.0, 0.0, 0.0, 1.0);

    gl_Position = distortion * projection * view * model * gl_Vertex;

    v_intensity = min(1.0, a_intensity + mod(int(gl_Position.x*160), 2)/20);
    v_bright = u_bright;
    v_dark = u_dark;
}