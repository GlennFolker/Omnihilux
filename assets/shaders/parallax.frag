varying vec2 v_texCoord1;
varying vec2 v_texCoord2;

uniform sampler2D u_texture1;
uniform sampler2D u_texture2;

void main() {
    vec4 col_1 = texture2D(u_texture1, v_texCoord1);
    vec4 col_2 = texture2D(u_texture2, v_texCoord2);
    gl_FragColor = col_1 * col_1.a + col_2 * col_2.a;
}