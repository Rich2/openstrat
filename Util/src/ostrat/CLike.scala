/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

trait CLikeCon
object JustCLikeText
{
   //implicit def impstringToCLike(s: String): JustCLikeText = new JustCLikeText(s)
}
//case class JustCLikeText(str: String) extends CLikeCon with IndText
trait CLikeSt extends CLikeCon
trait CBrace extends Indenter

//case class VMain(cMems: Seq[CLikeCon]) extends Indenter
//{
//   def out(indent: Int): String = "void main()" - cMems.curlyedOut(indent)    
//}