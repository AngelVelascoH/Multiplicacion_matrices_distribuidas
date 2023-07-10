# Multiplicacion_matrices_distribuidas
Programa que realiza una multiplicación de matrices de manera distribuida (Azure)

Ejecución:
Primero ejecutamos los nodos “Servidores”

![image](https://github.com/AngelVelascoH/Multiplicacion_matrices_distribuidas/assets/86260733/087fc033-cec0-479b-8236-1ba578ef95c2)

Luego vamos con el “cliente” recordando que son el mismo programa, para eso
usamos el paso de mensajes.

![image](https://github.com/AngelVelascoH/Multiplicacion_matrices_distribuidas/assets/86260733/29a4a1a4-8744-4535-b5c4-f3ef93f9b8d3)


Para agilizar el proceso de prueba, se pregunta por el tamaño de la matriz,
comencemos con 12, para ver las matrices y su checksum

![image](https://github.com/AngelVelascoH/Multiplicacion_matrices_distribuidas/assets/86260733/5c1597c3-2576-4482-8d0b-29ad67ccb665)

Vemos como cada nodo recibe la conexión y termina su trabajo (la multiplicación
distribuida)
Si hacemos un poco más visible la máquina local, veremos las matrices y el checksum

![image](https://github.com/AngelVelascoH/Multiplicacion_matrices_distribuidas/assets/86260733/114c37c4-17c0-4726-85b2-c8091653a234)

Ahora, sin necesidad de restaurar los servidores, probaremos con N= 3000, tardará un poco, pero dará el resultado.
(En caso de que el tamaño de la matriz sea muy grande, se mostrará solo el checksum)

![image](https://github.com/AngelVelascoH/Multiplicacion_matrices_distribuidas/assets/86260733/7bce53ea-9501-4c48-9beb-6d0272f041f8)





