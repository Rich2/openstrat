/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package geom
package pDisp

trait CanvSaver extends CanvDisp
{
   
   def save(fileName: String, output: String): Unit
   def load(fileName: String): EMon[String]
}

trait CanvBrowser extends CanvDisp
{
   def load(fileName: String, f: EMon[String] => Unit): Unit
}