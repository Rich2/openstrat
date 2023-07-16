/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

sealed trait TextLines {

}

case class TextInLine(text: String, length: Int) extends TextLines
case class TextOVerFlow(text: String, length2: Int) extends TextLines
case class TextOwnLines(text: String, numLines: Int) extends TextLines