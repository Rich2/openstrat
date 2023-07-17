/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

sealed trait TextLines {

}

case class TextIn1Line(text: String, length: Int) extends TextLines
case class TextIn2Line(text: String, length2: Int) extends TextLines
case class TextOwnLines(text: String, numLines: Int) extends TextLines