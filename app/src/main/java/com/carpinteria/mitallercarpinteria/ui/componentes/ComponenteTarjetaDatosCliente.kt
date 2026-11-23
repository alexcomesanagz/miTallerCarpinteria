package com.carpinteria.mitallercarpinteria.ui.componentes

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carpinteria.mitallercarpinteria.ui.theme.*

@Composable
fun TarjetaDatosCliente(
    nombreCliente: String,
    telefono: String,
    onLlamarClick: ((String) -> Unit)? = null
) {
    val context = LocalContext.current

        // Tarjeta de datos del cliente
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackground),
            border = BorderStroke(3.dp, CardBorder)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = null,
                        tint = WoodPrimary,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "DATOS DEL CLIENTE",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = WoodPrimary,
                        letterSpacing = 0.5.sp
                    )
                }

                Text(
                    text = nombreCliente,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    color = TextDarkBrown
                )

                BotonLlamar(
                    telefono = telefono,
                    onLlamarClick = {
                        if (onLlamarClick != null) {
                            onLlamarClick(telefono)
                        } else {
                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$telefono"))
                            context.startActivity(intent)
                        }
                    }
                )
            }
        }
    }


@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun TarjetaDatosClientePreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            TarjetaDatosCliente(
                nombreCliente = "Carmen Ruiz",
                telefono = "612 345 780"
            )
        }
    }
}