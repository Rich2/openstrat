package ostrat; package geom
import pWeb.*

object HtmlApp extends App
{
  val r2 = HtmlOlWithLH("Run <code>sbt</code> in bash from project's root folder. From within the sbt console run:")
  val r3 = r2.outLines(0, 0, 160)
  println("Finishing")
}
