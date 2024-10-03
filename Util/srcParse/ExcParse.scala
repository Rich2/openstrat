/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

trait ExcParse extends Exception

case class ExcAst(tp: TextPosn, detail: String) extends Exception(tp.fileName -- tp.lineNum.toString + ", " + tp.linePosn.toString + ": " + detail) with
  ExcParse

case class ExcLexar(tp: TextPosn, detail: String) extends Exception(tp.fileName -- tp.lineNum.toString + ", " + tp.linePosn.toString + ": " + detail) with
  ExcParse