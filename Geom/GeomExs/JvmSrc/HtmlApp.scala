package ostrat; package geom
import pWeb.*

object HtmlApp extends App
{
  val r2 = RArr(HtmlA("house.com", "House"), "Lets talk about Houses")
  val r3 = r2.outLines(0, 0, 160)
  println(r3.text)
}
