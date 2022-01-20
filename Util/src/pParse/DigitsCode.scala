/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

trait DigitCode extends ExprToken
{
   def digitSeqs: Arr[String]
}

case class FloatToken(srcStr: String) extends DigitCode{
  override def digitSeqs: Arr[String] = ???

  override def subTypeStr: String = ???

  override def startPosn: TextPosn = ???
}