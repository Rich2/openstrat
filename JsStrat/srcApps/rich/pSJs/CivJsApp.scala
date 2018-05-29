/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich
package pSJs

object CivJsApp// extends scala.scalajs.js.JSApp
{
   import pCiv._
   def main(args: Array[String]): Unit = new CivGui(CanvDispJs)   
} 