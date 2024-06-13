/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import java.net.*, java.io.{ BufferedReader, InputStreamReader }

object ServRaw extends App
{ deb("Starting")
  val sock: ServerSocket = new ServerSocket(8080)
  val cl: Socket = sock.accept()
  val readbuf: BufferedReader = new java.io.BufferedReader(new java.io.InputStreamReader(cl.getInputStream()))
  var line: String = null
  while ( { line = readbuf.readLine; line != null && line != "" }) {
    println(line)
  }
  cl.getOutputStream.write("HTTP/1.1 200 OK\nContent-Type: text/plain\n\nHello, Server with Http!".getBytes)
  readbuf.close
  sock.close()
  deb("Finishing")
}