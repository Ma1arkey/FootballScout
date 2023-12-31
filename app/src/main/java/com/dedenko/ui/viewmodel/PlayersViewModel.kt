package com.dedenko.ui.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedenko.data.repository.PlayersRepository
import com.dedenko.data.response.players.PlayersResponse
import com.dedenko.ui.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel  @Inject constructor(private val playersRepo: PlayersRepository) : ViewModel() {

    //get premier league teams using live data (I could also use stateflow, anyone:))
    private val _premierLeagueTeamPlayers = MutableLiveData<Resource<PlayersResponse>>()
    val premierLeagueTeamPlayers: LiveData<Resource<PlayersResponse>> = _premierLeagueTeamPlayers

    fun doGetPremierLeagueTeams(id: String){
        viewModelScope.launch {
            playersRepo.getPremierLeagueTeamPlayers(id)
                .catch { e->
                    _premierLeagueTeamPlayers.value = Resource.error(e.toString())
                }
                .collect {
                    _premierLeagueTeamPlayers.value = it
                }
        }
    }

}