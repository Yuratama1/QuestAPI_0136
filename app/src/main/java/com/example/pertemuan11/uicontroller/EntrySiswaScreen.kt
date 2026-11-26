package com.example.pertemuan11.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan11.ui.komponen.SiswaTopAppBar
import com.example.pertemuan11.viewmodel.DetailSiswa
import com.example.pertemuan11.viewmodel.EntryViewModel
import com.example.pertemuan11.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntrySiswaScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EntryViewModel = viewModel(
        factory = PenyediaViewModel.Factory
    )
) {
    val scope = rememberCoroutineScope()

    val scrollBehavior =
        TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(
            scrollBehavior.nestedScrollConnection
        ),
        topBar = {
            SiswaTopAppBar(
                title = "Tambah Data Siswa",
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->

        EntrySiswaBody(
            detailSiswa = viewModel.uiStateSiswa.detailSiswa,
            isEntryValid = viewModel.uiStateSiswa.isEntryValid,
            onSiswaValueChange = viewModel::updateUiState,
            onSaveClick = {
                scope.launch {
                    viewModel.addSiswa()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntrySiswaBody(
    detailSiswa: DetailSiswa,
    isEntryValid: Boolean,
    onSiswaValueChange: (DetailSiswa) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        FormTambahSiswa(
            detailSiswa = detailSiswa,
            onValueChange = onSiswaValueChange
        )

        Button(
            onClick = onSaveClick,
            enabled = isEntryValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan")
        }
    }
}

@Composable
fun FormTambahSiswa(
    detailSiswa: DetailSiswa,
    onValueChange: (DetailSiswa) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = detailSiswa.nama,
            onValueChange = {
                onValueChange(
                    detailSiswa.copy(nama = it)
                )
            },
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = detailSiswa.alamat,
            onValueChange = {
                onValueChange(
                    detailSiswa.copy(alamat = it)
                )
            },
            label = { Text("Alamat") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = detailSiswa.telpon,
            onValueChange = {
                onValueChange(
                    detailSiswa.copy(telpon = it)
                )
            },
            label = { Text("Telepon") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
    }
}