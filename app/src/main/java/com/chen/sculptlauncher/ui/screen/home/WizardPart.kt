package com.chen.sculptlauncher.ui.screen.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Cabin
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.chen.sculptlauncher.MainActivity
import com.chen.sculptlauncher.R

@Composable
fun WizardPart(context: Context, agreeApp: () -> Unit) {
    ConstraintLayout {
        val (header, footer) = createRefs()
        Column(
            modifier = Modifier.constrainAs(header){
                top.linkTo(parent.top, 32.dp)
                start.linkTo(parent.start); end.linkTo(parent.end)
            }.padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_icon),
                contentDescription = "app icon",
                modifier = Modifier
                    .size(64.dp)
                    .padding(bottom = 4.dp)
            )
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = stringResource(id = R.string.app_description),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )
            Box(modifier = Modifier.size(16.dp))
            ListItem(
                headlineContent = {
                    Text(text = stringResource(id = R.string.wizard_func_read_license))
                },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Default.Policy,
                        contentDescription = context.resources.getString(
                            R.string.wizard_func_read_license)
                    )
                },
                trailingContent = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowForward,
                        contentDescription = "go"
                    )
                },
                modifier = Modifier.clickable {
                    // TODO: 用户服务协议应当出现在这里
                }
            )
            ListItem(
                headlineContent = {
                    Text(text = stringResource(id = R.string.wizard_func_go_repo))
                },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Default.Cabin,
                        contentDescription = context.resources.getString(
                            R.string.wizard_func_go_repo)
                    )
                },
                trailingContent = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowForward,
                        contentDescription = "go"
                    )
                },
                modifier = Modifier.clickable {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setData(Uri.parse("https://github.com/Chen-asm/SculptLauncher"))
                    context.startActivity(intent)
                }
            )
        }
        Column(
            modifier = Modifier.constrainAs(footer){
                bottom.linkTo(parent.bottom, 8.dp)
                start.linkTo(parent.start); end.linkTo(parent.end)
            }.padding(horizontal = 16.dp)
        ) {
            HorizontalDivider()
            Text(
                text = stringResource(id = R.string.app_description_2),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
            )
            Text(
                text = stringResource(id = R.string.app_description_3),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth()
            )
            Box(modifier = Modifier.size(4.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { MainActivity.host.finish() }) {
                    Text(text = stringResource(id = R.string.wizard_cancel))
                }
                Box(modifier = Modifier.size(8.dp))
                ElevatedButton(onClick = agreeApp) {
                    Text(text = stringResource(id = R.string.wizard_ok))
                }
            }
        }
    }
}
