/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import scalajs.js.annotation._, pchess._

@JSExportTopLevel("ChessJsApp")
object ChessJsApp
{ @JSExport def main(): Unit = ChessGui(CanvasJs, ChessStart)
} 