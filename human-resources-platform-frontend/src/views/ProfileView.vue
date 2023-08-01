<script setup lang="ts">



</script>


<template>
    <Navbar />
    <div class="container">
        <div class="mt-3 mb-1">
            <ProfileCard />
        </div>
        <WalletCard v-bind:account="account.value"> </WalletCard>
    </div>
    <div class="row m-5">
        <div class="btn-group" role="group" aria-label="Basic example">
            <button type="button" class="btn btn-outline-primary w-25"
                @click="setDisplayOwnedNfts">Owned Nfts</button>
            <button type="button" class="btn btn-outline-primary w-25"
                @click="setDisplayNftMetadatas">Nft Metadatas</button>
        </div>
    </div>
    <div class="row align-content-center">
        <div class="mt-4 mb-4">
            <NftCardGroup :nfts=nfts.value v-if="displayOwnedNfts.value"></NftCardGroup>
            <NftMetadataCardGroup :nft-metadatas="nftMetadatas.value" v-if="displayNftMetadatas.value"></NftMetadataCardGroup>
        </div>
    </div>
</template>


<style></style>