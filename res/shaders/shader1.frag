#version 120

uniform sampler2D texture;

varying vec4 v_color;

void main() {
    gl_FragColor = texture2D(texture, gl_TexCoord[0].st) * v_color;
}