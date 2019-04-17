#version 120

varying float v_intensity;
varying vec3 v_bright;
varying vec3 v_dark;

void main() {
    gl_FragColor = vec4((v_bright - v_dark) * v_intensity + v_dark, 1.0);
//    gl_FragColor = vec4(v_bright, 1.0);
}