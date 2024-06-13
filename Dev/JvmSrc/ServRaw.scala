/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import java.net.ServerSocket

object ServRaw extends App
{ deb("Starting")
  val sock = new ServerSocket(8080)
  val cl = sock.accept()
  val st1 = new java.io.BufferedReader(new java.io.InputStreamReader(cl.getInputStream()))
  var line: String = null
  while ( { line = st1.readLine; line != null && line != "" }) {
    println(line)
  }
  cl.getOutputStream.write("HTTP/1.1 200 OK\nContent-Type: text/plain\n\nHello, Server with Http!".getBytes)
  st1.close
  sock.close()
  deb("Finishing")
}