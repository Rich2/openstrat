/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pSJs

//import pDisp._
//import geom._
//import org.scalajs._
//import scala.scalajs._
//import dom._
//
class CanvWebGl //extends CanvasJs
//{   
//   import raw.WebGLRenderingContext._
//   var gl: raw.WebGLRenderingContext = can.getContext("webgl").asInstanceOf[raw.WebGLRenderingContext]
//   gl.viewport(0, 0, can.width, can.height)
//   gl.clearColor(1.0, 1.0, 1.0, 1.0)//(0.4, 0.0, 0.5, 0.8)
//   gl.clear(COLOR_BUFFER_BIT)     
//     
//      var vShader = gl.createShader(VERTEX_SHADER)     
//      var vertText = "attribute vec2 position;".nl -
//         "attribute vec4 col;".nl -
//         "varying vec4 vColor;".nl -
//         VMain(Seq("gl_Position = vec4(position, 0, 1); vColor = col;")).out(0)
//      println(vertText)
//      gl.shaderSource(vShader, vertText)     
//      gl.compileShader(vShader)       
//     
//      //var tempVertices: js.Array[Float] = js.Array[Float]()
////      HexCentre.triangleFan.foreach(v => tempVertices.push(v.x.toFloat / 5, v.y.toFloat / 5))
////      
////      HexCentre.triangleFan.foreach(v => println(v.x, v.y))
////      //tempVertices.push(0.0f, 0.0f, -0.3f,-0.3f,  0.3f,-0.3f, 0.4f, 0.0f)//,  0.2f,0.2f,  0.6f, 0.6f)     
//      import js.typedarray.Float32Array
////      //var vertices: Float32Array = new Float32Array(tempVertices)
////      def out(dispObjs: Seq[CanvObj]): Unit = //x: Double, y: Double, width: Double, height: Double, colour: Colour) =
////      {
////         val verts2 = dispObjs.flatMap(_ match 
////            {
////               case rec: RectFill =>
////               {
////                  val xl = rec.x * 2 / cWidth - 1
////                  val xr = (rec.x + rec.width) * 2 / cWidth - 1
////                  val yb = rec.y * 2/ cHeight - 1
////                  val yt = (rec.y + rec.height) * 2 / cHeight - 1
////                  val col = rec.colour
////                  Seq(GlVert(xl, yb, col), GlVert(xl, yt, col), GlVert(xr, yb, col), GlVert(xr, yb, col),
////                    GlVert(xl, yt, col), GlVert(xr, yt, col))
////               }
////               case _ => Seq()
////            })
////         triangles(verts2)
////      }
//      
//      def triangles(inp: Seq[GlVert]): Unit =
//      {
//         var fShader = gl.createShader(FRAGMENT_SHADER)
//         var fragText = "precision highp float;".nl - "varying vec4 vColor;".nl - VMain(Seq("gl_FragColor = vColor;")).out(0)
//         println(fragText)
//         gl.shaderSource(fShader, fragText)
//         gl.compileShader(fShader)
//     
//         var program = gl.createProgram()//.asInstanceOf[scalajs.js.Dynamic]
//         gl.attachShader(program, vShader)
//         gl.attachShader(program, fShader)
//         gl.linkProgram(program)   
//      
//      val vertices = new Float32Array(inp.length * 6)//js.Array(
//    //-0.3f,-0.3f,  0.3f,-0.3f,  0.0f,0.3f//,  0.2f,0.2f,
//    //0.6f,0.6f,  0.4f,-0.4f
//    //))
//      inp.zipWithIndex.foreach(p =>
//         {
//            vertices(p._2 * 6) = p._1.x.toFloat
//            vertices(p._2 * 6 + 1) = p._1.y.toFloat
//            vertices(p._2 * 6 + 2) = p._1.colour.redGl
//            vertices(p._2 * 6 + 3) = p._1.colour.greenGl
//            vertices(p._2 * 6 + 4) = p._1.colour.blueGl
//            vertices(p._2 * 6 + 5) = 1.0f//colour.redGl
//         })
//      var buffer = gl.createBuffer()
//      gl.bindBuffer(ARRAY_BUFFER, buffer)
//      gl.bufferData(ARRAY_BUFFER, vertices, STATIC_DRAW)
//
//      gl.useProgram(program)
//      var progDyn = program.asInstanceOf[scalajs.js.Dynamic]
//      //progDyn.color = gl.getUniformLocation(program, "color")
//      //var temp2 = scalajs.js.Array[Double]()
//      //temp2.push(0f, 1f, 0.5f, 1.0f)
//      //gl.uniform4fv(progDyn.color.asInstanceOf[raw.WebGLUniformLocation], temp2)
//
//      progDyn.position = gl.getAttribLocation(program, "position")
//      gl.enableVertexAttribArray(progDyn.position.asInstanceOf[Int])
//      gl.vertexAttribPointer(progDyn.position.asInstanceOf[Int], 2, FLOAT, false, 24, 0)
//      progDyn.vertexColorAttribute = gl.getAttribLocation(program, "col");
//      gl.enableVertexAttribArray(progDyn.vertexColorAttribute.asInstanceOf[Int]);
//      gl.vertexAttribPointer(progDyn.vertexColorAttribute.asInstanceOf[Int], 4, FLOAT, false, 24, 8)
//      
//      gl.drawArrays(TRIANGLES, 0, vertices.length / 6)  
//      }
//   
//   override def tlPolyFill(verts: Vec2A, colour: Colour): Unit = {}
//   override def tlPolyDraw(verts: Vec2A, lineWidth: Double, lineColour: Colour): Unit = {}
//   override def tlPolyFillDraw(verts: Vec2A, colour: Colour, lineWidth: Double, borderColour: Colour): Unit = {}
//   
//   override def tlShapeFillDraw(segs: Seq[ShapeSeg], fillColour: Colour, lineWidth: Double, borderColour: Colour = Colour.Black): Unit = ???
//   override def tlShapeDraw(segs: Seq[ShapeSeg], lineWidth: Double, lineColour: Colour): Unit = ???
//   
//   override def clear(colour: Colour): Unit = {  }   
//   override def tlTextFill(x: Double, y: Double, text: String, fontSize: Int, colour: Colour): Unit = {}
//   override def tlTextDraw(x: Double, y: Double, text: String, fontSize: Int, colour: Colour): Unit = {}
//   override def tlClip(pts: Vec2A): Unit = {}
//  
//}