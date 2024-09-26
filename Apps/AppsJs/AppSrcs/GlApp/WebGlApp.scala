/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import scalajs._,js.annotation._, org.scalajs.dom._

@JSExportTopLevel("WebGlApp")
object WebGlApp
{
  @JSExport def main(args: Array[String]): Unit =
  {
    val can: html.Canvas = document.getElementById("scanv").asInstanceOf[html.Canvas]
    document.body.appendChild(can)
    import org.scalajs.dom.{ WebGLRenderingContext => rc}
    val gl: WebGLRenderingContext = can.getContext("webgl").asInstanceOf[WebGLRenderingContext]
    gl.clearColor(0.4, 0.0, 0.2, 0.8)
    gl.clear(rc.COLOR_BUFFER_BIT)
     
    val vShader = gl.createShader(rc.VERTEX_SHADER)
    val vertText = "attribute vec2 position;" --- VMain(Seq("gl_Position = vec4(position, 0, 1);")).out(0)
    gl.shaderSource(vShader, vertText)
    gl.compileShader(vShader)
     
    val fShader = gl.createShader(rc.FRAGMENT_SHADER)
    val fragText = "precision highp float;" --- "uniform vec4 color;" --- VMain(Seq("gl_FragColor = vec4(0, 1, 0, 1);")).out(0)
    gl.shaderSource(fShader, fragText)
    gl.compileShader(fShader)
     
    val program = gl.createProgram()
    gl.attachShader(program, vShader)
    gl.attachShader(program, fShader)
    gl.linkProgram(program)
     
    val tempVertices: js.Array[Float] = js.Array[Float]()
    //HexGridAncient.triangleFan.foreach(v => tempVertices.push(v.x.toFloat / 5, v.y.toFloat / 5))
      
    //HexGridAncient.triangleFan.foreach(v => println(""))//v.str1))
    tempVertices.push(0.0f, 0.0f, -0.3f,-0.3f,  0.3f,-0.3f, 0.4f, 0.0f)//,  0.2f,0.2f,  0.6f, 0.6f)
    import js.typedarray.Float32Array
    //var vertices: Float32Array = new Float32Array(tempVertices)
    val vertices = new Float32Array(js.Array(
    -0.3f,-0.3f,  0.3f,-0.3f,  0.0f,0.3f,  0.2f,0.2f,
    0.6f,0.6f,  0.4f,-0.4f))
      
    val buffer = gl.createBuffer()
    gl.bindBuffer(rc.ARRAY_BUFFER, buffer)
    gl.bufferData(rc.ARRAY_BUFFER, vertices, rc.STATIC_DRAW)

    gl.useProgram(program)
    val progDyn = program.asInstanceOf[scalajs.js.Dynamic]
    progDyn.color = gl.getUniformLocation(program, "color")
    val temp2 = scalajs.js.Array[Double]()
    temp2.push(0f, 1f, 0.5f, 1.0f)
    gl.uniform4fv(progDyn.color.asInstanceOf[WebGLUniformLocation], temp2)

    progDyn.position = gl.getAttribLocation(program, "position")
    gl.enableVertexAttribArray(progDyn.position.asInstanceOf[Int])
    gl.vertexAttribPointer(progDyn.position.asInstanceOf[Int], 2, rc.FLOAT, false, 0, 0)
    gl.drawArrays(rc.TRIANGLE_FAN, 0, vertices.length / 2)
  }
}