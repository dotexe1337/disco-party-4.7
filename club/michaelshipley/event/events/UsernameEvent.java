package club.michaelshipley.event.events;

import com.mojang.authlib.GameProfile;

import club.michaelshipley.event.Event;

public class UsernameEvent extends Event {
	
	String username;
	
	public UsernameEvent(String username) {
		this.username = username;
	}

	public String getProfile() {
		return username;
	}

	public void setProfile(String profile) {
		this.username = profile;
	}
	
}
