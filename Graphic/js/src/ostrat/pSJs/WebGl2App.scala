package ostrat
package p3dimport scalajs.js.annotation._

@JSExportTopLevel("WebGl2App")
object WebGl2App
{
  import raw.WebGLRenderingContext._
  var gl: raw.WebGLRenderingContext = can.getContext("webgl2").asInstanceOf[raw.WebGL2RenderingContext]
}
