attribute vec2 a_position;
attribute vec2 a_texCoord0;

varying vec2 v_texCoord1;
varying vec2 v_texCoord2;

uniform vec2 u_resolution;
uniform vec2 u_position;

uniform vec2 u_dimension1;
uniform float u_intensity1;
uniform vec2 u_dimension2;
uniform float u_intensity2;

void main() {
    gl_Position = vec4(a_position, 1.0, 1.0);
    vec2 pos = vec2(u_position.x, u_position.y);

    vec2 scale1 = u_resolution / u_dimension1;
    v_texCoord1 = a_texCoord0 * scale1 - (0.5 * scale1) + (pos * u_intensity1) / (u_dimension1 * 2.0);

    vec2 scale2 = u_resolution / u_dimension2;
    v_texCoord2 = a_texCoord0 * scale2 - (0.5 * scale2) + (pos * u_intensity2) / (u_dimension2 * 2.0);
}
