/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import scalajs._,js.annotation._, org.scalajs.dom._, pGrid._

@JSExportTopLevel("WebGlApp")
object WebGlApp
{
  @JSExport def main(): Unit =
  {
    val can: html.Canvas = document.getElementById("scanv").asInstanceOf[html.Canvas]
    document.body.appendChild(can)
    import raw.WebGLRenderingContext._
    val gl: raw.WebGLRenderingContext = can.getContext("webgl").asInstanceOf[raw.WebGLRenderingContext]
    gl.clearColor(0.4, 0.0, 0.2, 0.8)
    gl.clear(COLOR_BUFFER_BIT)
     
    val vShader = gl.createShader(VERTEX_SHADER)
    val vertText = "attribute vec2 position;" --- VMain(Seq("gl_Position = vec4(position, 0, 1);")).out(0)
    gl.shaderSource(vShader, vertText)
    gl.compileShader(vShader)
     
    val fShader = gl.createShader(FRAGMENT_SHADER)
    val fragText = "precision highp float;" --- "uniform vec4 color;" --- VMain(Seq("gl_FragColor = vec4(0, 1, 0, 1);")).out(0)
    gl.shaderSource(fShader, fragText)
    gl.compileShader(fShader)
     
    val program = gl.createProgram()
    gl.attachShader(program, vShader)
    gl.attachShader(program, fShader)
    gl.linkProgram(program)
     
    val tempVertices: js.Array[Float] = js.Array[Float]()
    HexGridAncient.triangleFan.foreach(v => tempVertices.push(v.x.toFloat / 5, v.y.toFloat / 5))
      
    HexGridAncient.triangleFan.foreach(v => println(""))//v.str1))
    tempVertices.push(0.0f, 0.0f, -0.3f,-0.3f,  0.3f,-0.3f, 0.4f, 0.0f)//,  0.2f,0.2f,  0.6f, 0.6f)
    import js.typedarray.Float32Array
    //var vertices: Float32Array = new Float32Array(tempVertices)
    val vertices = new Float32Array(js.Array(
    -0.3f,-0.3f,  0.3f,-0.3f,  0.0f,0.3f,  0.2f,0.2f,
    0.6f,0.6f,  0.4f,-0.4f))
      
    val buffer = gl.createBuffer()
    gl.bindBuffer(ARRAY_BUFFER, buffer)
    gl.bufferData(ARRAY_BUFFER, vertices, STATIC_DRAW)

    gl.useProgram(program)
    val progDyn = program.asInstanceOf[scalajs.js.Dynamic]
    progDyn.color = gl.getUniformLocation(program, "color")
    val temp2 = scalajs.js.Array[Double]()
    temp2.push(0f, 1f, 0.5f, 1.0f)
    gl.uniform4fv(progDyn.color.asInstanceOf[raw.WebGLUniformLocation], temp2)

    progDyn.position = gl.getAttribLocation(program, "position")
    gl.enableVertexAttribArray(progDyn.position.asInstanceOf[Int])
    gl.vertexAttribPointer(progDyn.position.asInstanceOf[Int], 2, FLOAT, false, 0, 0)
    gl.drawArrays(TRIANGLE_FAN, 0, vertices.length / 2)
  }
}